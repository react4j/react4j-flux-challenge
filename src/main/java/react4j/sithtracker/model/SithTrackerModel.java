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
import javax.annotation.Nullable;

@ArezComponent( service = Feature.ENABLE, requireId = Feature.DISABLE, disposeNotifier = Feature.DISABLE )
public abstract class SithTrackerModel
{
  private static final int DARTH_SIDIOUS_ID = 3616;
  private static final int MAX_VISIBLE_SITHS = 5;
  private static final int NUMBER_OF_SITHS_TO_SCROLL = 2;
  @Nullable
  private WebSocket _webSocket;
  @Nonnull
  private final List<SithPlaceholder> _siths = new ArrayList<>( MAX_VISIBLE_SITHS );

  SithTrackerModel()
  {
    for ( int i = 0; i < MAX_VISIBLE_SITHS; i++ )
    {
      _siths.add( null );
    }
  }

  @PostConstruct
  final void postConstruct()
  {
    _webSocket = new WebSocket( "ws://localhost:4000" );
    _webSocket.onmessage = msg -> setCurrentPlanet( Planet.parse( String.valueOf( msg.data ) ) );
    loadSith( DARTH_SIDIOUS_ID, 2 );
    setCurrentPlanet( Planet.create( -1, "" ) );
  }

  @PreDispose
  final void preDispose()
  {
    assert null != _webSocket;
    _webSocket.close();
    for ( int i = 0; i < MAX_VISIBLE_SITHS; i++ )
    {
      clearSithLordAt( i );
    }
  }

  @Memoize
  public boolean canScrollUp()
  {
    if ( anySithsOnCurrentPlanet() )
    {
      return false;
    }
    else
    {
      final SithModel sith = getSiths().get( 0 );
      return null != sith && null != sith.getData().getMasterId();
    }
  }

  @Action
  public void scrollUp()
  {
    if ( canScrollUp() )
    {
      for ( int i = MAX_VISIBLE_SITHS - NUMBER_OF_SITHS_TO_SCROLL; i < MAX_VISIBLE_SITHS; i++ )
      {
        clearSithLordAt( i );
      }
      for ( int i = MAX_VISIBLE_SITHS - 1; i >= NUMBER_OF_SITHS_TO_SCROLL; i-- )
      {
        moveSithLord( i - NUMBER_OF_SITHS_TO_SCROLL, i );
      }
      loadSithGenealogy( _siths.get( NUMBER_OF_SITHS_TO_SCROLL ) );
      getSithsComputableValue().reportPossiblyChanged();
    }
  }

  @Memoize
  public boolean canScrollDown()
  {
    if ( anySithsOnCurrentPlanet() )
    {
      return false;
    }
    else
    {
      final SithModel sith = getSiths().get( MAX_VISIBLE_SITHS - 1 );
      return null != sith && null != sith.getData().getApprenticeId();
    }
  }

  @Action
  public void scrollDown()
  {
    if ( canScrollDown() )
    {
      for ( int i = 0; i < NUMBER_OF_SITHS_TO_SCROLL; i++ )
      {
        clearSithLordAt( i );
      }
      for ( int i = NUMBER_OF_SITHS_TO_SCROLL; i < MAX_VISIBLE_SITHS; i++ )
      {
        moveSithLord( i, i - NUMBER_OF_SITHS_TO_SCROLL );
      }
      loadSithGenealogy( _siths.get( MAX_VISIBLE_SITHS - NUMBER_OF_SITHS_TO_SCROLL - 1 ) );
      getSithsComputableValue().reportPossiblyChanged();
    }
  }

  @Memoize( depType = DepType.AREZ_OR_EXTERNAL )
  @Nonnull
  public List<SithModel> getSiths()
  {
    return _siths.stream().map( e -> null == e || e.isLoading() ? null : e.getSith() ).collect( Collectors.toList() );
  }

  @ComputableValueRef
  abstract ComputableValue<?> getSithsComputableValue();

  @Observable( writeOutsideTransaction = Feature.ENABLE, initializer = Feature.DISABLE )
  @Nonnull
  public abstract Planet getCurrentPlanet();

  abstract void setCurrentPlanet( @Nonnull Planet currentPlanet );

  @Action( verifyRequired = false )
  void loadSithGenealogy( @Nonnull final SithPlaceholder placeholder )
  {
    final int position = _siths.indexOf( placeholder );
    if ( -1 != position )
    {
      getSithsComputableValue().reportPossiblyChanged();
      final SithModel model = placeholder.getSith();
      final Sith sith = model.getData();
      final Double masterId = sith.getMasterId();
      if ( null != masterId && position > 0 && null == _siths.get( position - 1 ) )
      {
        loadSith( masterId.intValue(), position - 1 );
      }
      final Double apprenticeId = sith.getApprenticeId();
      if ( null != apprenticeId && position < 4 && null == _siths.get( position + 1 ) )
      {
        loadSith( apprenticeId.intValue(), position + 1 );
      }
    }
  }

  private void loadSith( final int sithId, final int position )
  {
    final SithPlaceholder placeholder = SithPlaceholder.create( sithId );
    _siths.set( position, placeholder );
    placeholder.load( () -> loadSithGenealogy( placeholder ) );
  }

  private boolean anySithsOnCurrentPlanet()
  {
    return getSiths().stream()
      .anyMatch( sith -> null != sith && getCurrentPlanet().getId() == sith.getData().getHomeworld().getId() );
  }

  private void clearSithLordAt( final int index )
  {
    final SithPlaceholder placeholder = _siths.get( index );
    _siths.set( index, null );
    Disposable.dispose( placeholder );
  }

  private void moveSithLord( final int fromIndex, final int toIndex )
  {
    _siths.set( toIndex, _siths.get( fromIndex ) );
    _siths.set( fromIndex, null );
  }
}
