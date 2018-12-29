package react4j.sithtracker.model;

import javax.annotation.Nullable;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "Object" )
public final class SithReference
{
  @Nullable
  private Double id;
  @Nullable
  private String url;

  @JsOverlay
  @Nullable
  public Double getId()
  {
    return id;
  }

  @JsOverlay
  @Nullable
  public String getUrl()
  {
    return url;
  }
}
