package react4j.sithtracker.api;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;

/**
 *
 * TODO: Why is AbortController not present in Elemental2?
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/AbortController/AbortController">AbortController</a>
 */
@JsType( isNative = true, namespace = "<window>", name = "AbortController" )
public final class AbortController
{
  @JsConstructor
  public AbortController()
  {
  }

  public AbortSignal signal;

  public native void abort();
}
