package react4j.sithtracker.model;

import elemental2.core.Global;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

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
    return (Sith) Global.JSON.parse( jsonData );
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
