package react4j.sithtracker.model;

import arez.SafeProcedure;
import arez.annotations.ArezComponent;
import arez.annotations.ComponentId;
import arez.annotations.Observable;
import elemental2.dom.DomGlobal;
import elemental2.dom.Response;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jsinterop.base.Any;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

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

  boolean isLoading()
  {
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
      .then( Response::json )
      .then( v -> {
        final JsPropertyMap<Object> data = Js.asPropertyMap( v );
        setSith( new Sith( _id,
                           data.getAny( "name" ).asString(),
                           data.getAny( "homeworld" ).cast(),
                           // When sith has no master, the master object is still returned but the id is null
                           maybeInt( data.getAny( "master" ).asPropertyMap().getAny( "id" ) ),
                           // When sith has no apprentice, the apprentice object is still returned but the id is null
                           maybeInt( data.getAny( "apprentice" ).asPropertyMap().getAny( "id" ) ) ) );
        onLoadComplete.call();
        return null;
      } )
      .catch_( error -> {
        DomGlobal.console.log( "Error loading sith " + getId(), error );
        return null;
      } );
  }

  @Nullable
  private Integer maybeInt( @Nullable final Any any )
  {
    return null == any ? null : any.asInt();
  }
}
