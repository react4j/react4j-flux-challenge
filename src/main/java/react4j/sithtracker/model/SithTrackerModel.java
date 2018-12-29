package react4j.sithtracker.model;

import arez.ComputableValue;
import arez.Disposable;
import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.ComputableValueRef;
import arez.annotations.DepType;
import arez.annotations.Feature;
import arez.annotations.Memoize;
import arez.annotations.Observable;
import arez.annotations.PostConstruct;
import arez.annotations.PreDispose;
import elemental2.dom.WebSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.inject.Singleton;

@Singleton
@ArezComponent
public abstract class SithTrackerModel
{
  private static final int DARTH_SIDIOUS_ID = 3616;
  private static final int ENTRY_COUNT = 5;
  private static final int STEP_SIZE = 2;
  private WebSocket _webSocket;
  @Nonnull
  private final ArrayList<SithPlaceholder> _siths = new ArrayList<>( ENTRY_COUNT );

  SithTrackerModel()
  {
    for ( int i = 0; i < ENTRY_COUNT; i++ )
    {
      _siths.add( null );
    }
  }

  @PostConstruct
  final void postConstruct()
  {
    _webSocket = new WebSocket( "ws://localhost:4000" );
    _webSocket.onmessage = msg -> {
      setCurrentPlanet( Planet.parse( String.valueOf( msg.data ) ) );
      //TODO: Why does elemental2 define a return type here?
      return null;
    };
    loadSith( DARTH_SIDIOUS_ID, 2 );
    setCurrentPlanet( Planet.create( -1, "" ) );
  }

  @PreDispose
  final void preDispose()
  {
    assert null != _webSocket;
    _webSocket.close();
  }

  @Memoize
  public boolean canScrollUp()
  {
    if ( areAnySithLordsOnCurrentPlanet() )
    {
      return false;
    }
    else
    {
      final Sith sith = getSiths().get( 0 );
      return null != sith && null != sith.getMasterId();
    }
  }

  private boolean areAnySithLordsOnCurrentPlanet()
  {
    return getSiths().stream()
      .anyMatch( sith -> null != sith && getCurrentPlanet().getId() == sith.getHomeworld().getId() );
  }

  @Action
  public void scrollUp()
  {
    if ( canScrollUp() )
    {
      for ( int i = ENTRY_COUNT - STEP_SIZE; i < ENTRY_COUNT; i++ )
      {
        clearSithLordAt( i );
      }
      for ( int i = ENTRY_COUNT - 1; i >= STEP_SIZE; i-- )
      {
        moveSithLord( i - STEP_SIZE, i );
      }
      loadSithGenealogy( _siths.get( STEP_SIZE ) );
    }
  }

  private void clearSithLordAt( final int index )
  {
    Disposable.dispose( _siths.get( index ) );
    _siths.set( index, null );
  }

  private void moveSithLord( final int fromIndex, final int toIndex )
  {
    _siths.set( toIndex, _siths.get( fromIndex ) );
    _siths.set( fromIndex, null );
  }

  @Memoize
  public boolean canScrollDown()
  {
    if ( areAnySithLordsOnCurrentPlanet() )
    {
      return false;
    }
    else
    {
      final Sith sith = getSiths().get( ENTRY_COUNT - 1 );
      return null != sith && null != sith.getApprenticeId();
    }
  }

  @Action
  public void scrollDown()
  {
    if ( canScrollDown() )
    {
      for ( int i = 0; i < STEP_SIZE; i++ )
      {
        clearSithLordAt( i );
      }
      for ( int i = STEP_SIZE; i < ENTRY_COUNT; i++ )
      {
        moveSithLord( i, i - STEP_SIZE );
      }
      loadSithGenealogy( _siths.get( ENTRY_COUNT - STEP_SIZE - 1 ) );
    }
  }

  @Memoize( depType = DepType.AREZ_OR_EXTERNAL )
  @Nonnull
  public List<Sith> getSiths()
  {
    return _siths.stream().map( e -> null == e || e.isLoading() ? null : e.getSith() ).collect( Collectors.toList() );
  }

  @ComputableValueRef
  abstract ComputableValue getSithsComputableValue();

  @Observable( writeOutsideTransaction = true, initializer = Feature.DISABLE )
  @Nonnull
  public abstract Planet getCurrentPlanet();

  abstract void setCurrentPlanet( @Nonnull Planet currentPlanet );

  private void loadSith( final int sithId, final int position )
  {
    final SithPlaceholder placeholder = SithPlaceholder.create( sithId );
    _siths.set( position, placeholder );
    placeholder.load( () -> loadSithGenealogy( placeholder ) );
  }

  @Action( verifyRequired = false )
  void loadSithGenealogy( @Nonnull final SithPlaceholder placeholder )
  {
    final int position = _siths.indexOf( placeholder );
    if ( -1 != position )
    {
      getSithsComputableValue().reportPossiblyChanged();
      final Sith sith = placeholder.getSith();
      final Integer masterId = sith.getMasterId();
      if ( null != masterId && position > 0 && null == _siths.get( position - 1 ) )
      {
        loadSith( masterId, position - 1 );
      }
      final Integer apprenticeId = sith.getApprenticeId();
      if ( null != apprenticeId && position < 4 && null == _siths.get( position + 1 ) )
      {
        loadSith( apprenticeId, position + 1 );
      }
    }
  }
}
