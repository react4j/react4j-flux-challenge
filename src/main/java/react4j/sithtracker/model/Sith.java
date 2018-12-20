package react4j.sithtracker.model;

import arez.annotations.ArezComponent;
import arez.annotations.Feature;
import arez.annotations.Observable;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@ArezComponent
public abstract class Sith
{
  @Nonnull
  public static Sith create( final int id,
                             @Nonnull final String name,
                             @Nonnull final World homeworld,
                             @Nullable final Integer masterId,
                             @Nullable final Integer apprenticeId )
  {
    return new Arez_Sith( id, name, homeworld, masterId, apprenticeId );
  }

  @Observable( initializer = Feature.ENABLE )
  public abstract int getId();

  public abstract void setId( int id );

  @Observable
  @Nonnull
  public abstract String getName();

  public abstract void setName( @Nonnull String name );

  @Observable
  @Nonnull
  public abstract World getHomeworld();

  public abstract void setHomeworld( @Nonnull World homeworld );

  @Observable( initializer = Feature.ENABLE )
  @Nullable
  public abstract Integer getMasterId();

  public abstract void setMasterId( @Nullable Integer masterId );

  @Observable( initializer = Feature.ENABLE )
  @Nullable
  public abstract Integer getApprenticeId();

  public abstract void setApprenticeId( @Nullable Integer apprenticeId );
}
