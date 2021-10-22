package react4j.sithtracker.model;

import akasha.AbortController;
import akasha.Console;
import akasha.DOMException;
import akasha.RequestInit;
import akasha.Response;
import akasha.WindowGlobal;
import arez.SafeProcedure;
import arez.annotations.ArezComponent;
import arez.annotations.ComponentId;
import arez.annotations.Feature;
import arez.annotations.Observable;
import arez.annotations.PreDispose;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@ArezComponent( disposeNotifier = Feature.DISABLE )
abstract class SithPlaceholder
{
  private final int _id;
  @Nullable
  private SithModel _sith;
  @Nullable
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
  int getId()
  {
    return _id;
  }

  @PreDispose
  void preDispose()
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
  SithModel getSith()
  {
    final SithModel sith = sith();
    assert null != sith;
    return sith;
  }

  @Observable( writeOutsideTransaction = Feature.ENABLE )
  @Nullable
  SithModel sith()
  {
    return _sith;
  }

  void setSith( @Nonnull final SithModel sith )
  {
    assert null == _sith;
    _sith = Objects.requireNonNull( sith );
  }

  void load( @Nonnull SafeProcedure onLoadComplete )
  {
    _abortController = new AbortController();
    WindowGlobal
      .fetch( "http://localhost:3000/dark-jedis/" + getId(), RequestInit.of().signal( _abortController.signal() ) )
      .then( Response::text )
      .thenAccept( v -> {
        _abortController = null;
        setSith( new SithModel( Sith.parse( v ) ) );
        onLoadComplete.call();
      } )
      .catch_( error -> {
        if ( !isAbortError( error ) )
        {
          Console.log( "Error loading sith " + getId(), error );
          _abortController = null;
        }
        return null;
      } );
  }

  private boolean isAbortError( final Object error )
  {
    return error instanceof DOMException && DOMException.ABORT_ERR == ( (DOMException) error ).code();
  }
}
