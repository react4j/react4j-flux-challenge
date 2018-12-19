package react4j.sithtracker.model;

import elemental2.core.Global;
import java.util.Objects;
import javax.annotation.Nonnull;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "Object" )
public final class World
{
  private int id;
  private String name;

  @JsOverlay
  @Nonnull
  public static World create( final int id, @Nonnull final String name )
  {
    final World world = new World();
    world.id = id;
    world.name = Objects.requireNonNull( name );
    return world;
  }

  @JsOverlay
  @Nonnull
  public static World parse( @Nonnull final String jsonData )
  {
    return (World) Global.JSON.parse( jsonData );
  }

  @JsOverlay
  public int getId()
  {
    return id;
  }

  @JsOverlay
  @Nonnull
  public String getName()
  {
    return name;
  }
}
