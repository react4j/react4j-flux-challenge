package react4j.sithtracker.model;

import arez.SafeProcedure;
import arez.annotations.ArezComponent;
import arez.annotations.ComponentId;
import arez.annotations.Feature;
import arez.annotations.Observable;
import arez.annotations.PreDispose;
import elemental2.dom.AbortController;
import elemental2.dom.DOMException;
import elemental2.dom.DomGlobal;
import elemental2.dom.RequestInit;
import elemental2.dom.Response;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;

@ArezComponent( disposeTrackable = Feature.DISABLE )
abstract class SithPlaceholder
{
  private final int _id;
  private Sith _sith;
  private AbortController _abortController;

  @Nonnull
  static SithPlaceholder create( final int id )
  {
    return new Arez_SithPlaceholder( id );
  }

  SithPlaceholder( final int id )
  {
    _id = id;
  }

  @ComponentId
  final int getId()
  {
    return _id;
  }

  @PreDispose
  final void preDispose()
  {
    cancelLoading();
  }

  private void cancelLoading()
  {
    if ( null != _abortController )
    {
      _abortController.abort();
      _abortController = null;
    }
  }

  boolean isLoading()
  {
    assert ( null != _sith ) == ( null == _abortController );
    return null == sith();
  }

  @Nonnull
  Sith getSith()
  {
    final Sith sith = sith();
    assert null != sith;
    return sith;
  }

  @Observable( writeOutsideTransaction = true )
  @Nullable
  Sith sith()
  {
    return _sith;
  }

  void setSith( @Nonnull final Sith sith )
  {
    assert null == _sith;
    _sith = Objects.requireNonNull( sith );
  }

  void load( @Nonnull SafeProcedure onLoadComplete )
  {
    _abortController = new AbortController();
    final RequestInit init = RequestInit.create();
    init.setSignal( _abortController.signal );
    DomGlobal
      .fetch( "http://localhost:3000/dark-jedis/" + getId(), init )
      .then( Response::text )
      .then( v -> {
        _abortController = null;
        setSith( Sith.parse( v ) );
        onLoadComplete.call();
        return null;
      } )
      .catch_( error -> {
        if ( !isAbortError( error ) )
        {
          DomGlobal.console.log( "Error loading sith " + getId(), error );
          _abortController = null;
        }
        return null;
      } );
  }

  private boolean isAbortError( final Object error )
  {
    return error instanceof DOMException && 20 == ( (DOMException) error ).code;
  }
}
