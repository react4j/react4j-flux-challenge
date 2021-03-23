package react4j.sithtracker.model;

import akasha.core.JSON;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@SuppressWarnings( "unused" )
@JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "Object" )
public final class Sith
{
  private int id;
  private String name;
  private Planet homeworld;
  private SithReference master;
  private SithReference apprentice;

  @JsOverlay
  @Nonnull
  public static Sith parse( @Nonnull final String jsonData )
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

  @JsOverlay
  @Nonnull
  public Planet getHomeworld()
  {
    return homeworld;
  }

  @JsOverlay
  @Nullable
  public Double getMasterId()
  {
    return master.getId();
  }

  @JsOverlay
  @Nullable
  public Double getApprenticeId()
  {
    return apprentice.getId();
  }
}
