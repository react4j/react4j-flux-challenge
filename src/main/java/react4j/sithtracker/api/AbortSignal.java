package react4j.sithtracker.api;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsType;

@JsType( isNative = true, namespace = "<window>", name = "AbortSignal" )
public final class AbortSignal
{
  @JsFunction
  public interface OnAbortFn
  {
    void onAbort();
  }

  public boolean aborted;

  public OnAbortFn onabort;
}
