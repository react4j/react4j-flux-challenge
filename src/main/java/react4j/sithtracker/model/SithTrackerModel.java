package react4j.sithtracker.model;

import arez.ComputableValue;
import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.ComputableValueRef;
import arez.annotations.DepType;
import arez.annotations.Memoize;
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
  private WebSocket _webSocket;
  @Nonnull
  private Planet _currentPlanet = Planet.create( -1, "" );
  @Nonnull
  private final ArrayList<SithPlaceholder> _siths = new ArrayList<>( 5 );

  SithTrackerModel()
  {
    _siths.add( null );
    _siths.add( null );
    _siths.add( null );
    _siths.add( null );
    _siths.add( null );
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
  }

  @PreDispose
  final void preDispose()
  {
    assert null != _webSocket;
    _webSocket.close();
  }

  public boolean canScrollUp()
  {
    return false;
  }

  public void scrollUp()
  {
  }

  public boolean canScrollDown()
  {
    return false;
  }

  public void scrollDown()
  {
  }

  @Memoize( depType = DepType.AREZ_OR_EXTERNAL )
  @Nonnull
  public List<Sith> getSithWindow()
  {
    return _siths.stream().map( e -> null == e || e.isLoading() ? null : e.getSith() ).collect( Collectors.toList() );
  }

  @ComputableValueRef
  abstract ComputableValue getSithWindowComputableValue();

  @Memoize( depType = DepType.AREZ_OR_EXTERNAL )
  @Nonnull
  public Planet getCurrentPlanet()
  {
    return _currentPlanet;
  }

  @ComputableValueRef
  @Nonnull
  abstract ComputableValue getCurrentPlanetComputableValue();

  @Action
  void setCurrentPlanet( @Nonnull final Planet currentPlanet )
  {
    _currentPlanet = currentPlanet;
    getCurrentPlanetComputableValue().reportPossiblyChanged();
  }

  private void loadSith( final int sithId, final int position )
  {
    final SithPlaceholder placeholder = SithPlaceholder.create( sithId );
    _siths.set( position, placeholder );
    placeholder.load( () -> loadSithGenealogy( placeholder ) );
  }

  @Action( verifyRequired = false )
  void completeSithLoad( @Nonnull final SithPlaceholder entry )
  {
    final int position = _siths.indexOf( entry );
    if ( -1 != position )
    {
      getSithWindowComputableValue().reportPossiblyChanged();
      final Sith sith = entry.getSith();
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
