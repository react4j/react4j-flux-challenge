package react4j.sithtracker.model;

import arez.SafeProcedure;
import arez.annotations.ArezComponent;
import arez.annotations.ComponentId;
import arez.annotations.Observable;
import arez.annotations.PreDispose;
import elemental2.dom.DomGlobal;
import elemental2.dom.Response;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.base.Any;

@ArezComponent
abstract class SithPlaceholder
{
  private final int _id;
  private Sith _sith;

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
    //TODO: Trigger abort controller here
    //if ( null != _abortController )
    //{
    //}
  }

  boolean isLoading()
  {
    //TODO: assert (null == _sith) == (null == _abortController);
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
    //TODO: AbortController - https://developer.mozilla.org/en-US/docs/Web/API/AbortController/AbortController
    // Is it not present in Elemental2?
    DomGlobal
      .fetch( "http://localhost:3000/dark-jedis/" + getId() )
      .then( Response::text )
      .then( v -> {
        setSith( Sith.parse( v ) );
        onLoadComplete.call();
        //TODO: Clear abort controller here
        return null;
      } )
      .catch_( error -> {
        DomGlobal.console.log( "Error loading sith " + getId(), error );
        //TODO: Clear abort controller here
        return null;
      } );
  }

  @Nullable
  private Integer maybeInt( @Nullable final Any any )
  {
    return null == any ? null : any.asInt();
  }
}
