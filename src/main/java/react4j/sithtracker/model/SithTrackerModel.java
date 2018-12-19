package react4j.sithtracker.model;

import arez.ComputableValue;
import arez.ObservableValue;
import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.ComputableValueRef;
import arez.annotations.DepType;
import arez.annotations.Memoize;
import arez.annotations.Observable;
import arez.annotations.ObservableValueRef;
import arez.annotations.PostConstruct;
import arez.annotations.PreDispose;
import elemental2.dom.WebSocket;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.inject.Singleton;

@Singleton
@ArezComponent
public abstract class SithTrackerModel
{
  private WebSocket _webSocket;
  @Nonnull
  private World _currentWorld = World.create( -1, "" );
  @Nonnull
  private final Sith[] _siths = new Sith[ 5 ];

  SithTrackerModel()
  {
  }

  @PostConstruct
  final void postConstruct()
  {
    _webSocket = new WebSocket( "ws://localhost:4000" );
    _webSocket.onmessage = msg -> {
      setCurrentWorld( World.parse( String.valueOf( msg.data ) ) );
      //TODO: Why does elemental2 define a return type here?
      return null;
    };
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

  @Observable( expectSetter = false )
  @Nonnull
  public List<Sith> getSithWindow()
  {
    return Arrays.asList( _siths );
  }

  @ObservableValueRef
  abstract ObservableValue getSithWindowObservableValue();

  @Memoize( depType = DepType.AREZ_OR_EXTERNAL )
  @Nonnull
  public World getCurrentWorld()
  {
    return _currentWorld;
  }

  @ComputableValueRef
  @Nonnull
  abstract ComputableValue getCurrentWorldComputableValue();

  @Action
  void setCurrentWorld( @Nonnull final World currentWorld )
  {
    _currentWorld = currentWorld;
    getCurrentWorldComputableValue().reportPossiblyChanged();
  }
}
