package react4j.sithtracker.model;

import akasha.core.JSON;
import java.util.Objects;
import javax.annotation.Nonnull;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "Object" )
public final class Planet
{
  private int id;
  private String name;

  @JsOverlay
  @Nonnull
  public static Planet create( final int id, @Nonnull final String name )
  {
    final Planet planet = new Planet();
    planet.id = id;
    planet.name = Objects.requireNonNull( name );
    return planet;
  }

  @JsOverlay
  @Nonnull
  public static Planet parse( @Nonnull final String jsonData )
  {
    return Objects.requireNonNull( JSON.parse( jsonData ) ).cast();
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
