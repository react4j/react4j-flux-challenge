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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.inject.Singleton;

@Singleton
@ArezComponent
public abstract class SithTrackerModel
{
  private WebSocket _webSocket;
  @Nonnull
  private Planet _currentPlanet = Planet.create( -1, "" );
  @Nonnull
  private final SithPlaceholder[] _siths = new SithPlaceholder[ 5 ];

  SithTrackerModel()
  {
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
    loadSith( 3616, 2 );
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
    return Arrays
      .stream( _siths )
      .map( e -> null == e || e.isLoading() ? null : e.getSith() )
      .collect( Collectors.toList() );
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
  abstract ComputableValue getCurrentWorldComputableValue();

  @Action
  void setCurrentPlanet( @Nonnull final Planet currentPlanet )
  {
    _currentPlanet = currentPlanet;
    getCurrentWorldComputableValue().reportPossiblyChanged();
  }

  void loadSith( final int sithId, final int position )
  {
    if ( null == _siths[ position ] )
    {
      final SithPlaceholder entry = SithPlaceholder.create( sithId );
      _siths[ position ] = entry;
      entry.load( () -> completeSithLoad( entry ) );
    }
    else
    {
      assert _siths[ position ].getId() == sithId;
    }
  }

  @Action( verifyRequired = false )
  void completeSithLoad( @Nonnull final SithPlaceholder entry )
  {
    getSithWindowComputableValue().reportPossiblyChanged();
    final int position = positionOf( entry );

    final Sith sith = entry.getSith();
    final Integer masterId = sith.getMasterId();
    if ( null != masterId && position > 0 && null == _siths[ position - 1 ] )
    {
      loadSith( masterId, position - 1 );
    }
    final Integer apprenticeId = sith.getApprenticeId();
    if ( null != apprenticeId && position < _siths.length - 1 && null == _siths[ position + 1 ] )
    {
      loadSith( apprenticeId, position + 1 );
    }
  }

  private int positionOf( @Nonnull final SithPlaceholder entry )
  {
    int position = -1;
    for ( int i = 0; i < _siths.length; i++ )
    {
      if ( _siths[ i ] == entry )
      {
        position = i;
        break;
      }
    }
    assert position >= 0;
    return position;
  }
}
