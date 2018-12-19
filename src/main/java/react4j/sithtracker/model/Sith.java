package react4j.sithtracker.model;

import arez.annotations.ArezComponent;
import arez.annotations.Observable;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@ArezComponent
public abstract class Sith
{
  @Observable
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

  @Observable
  @Nullable
  public abstract String getMaster();

  public abstract void setMaster( @Nullable String master );

  @Observable
  @Nullable
  public abstract String getApprentice();

  public abstract void setApprentice( @Nullable String apprentice );

  @Observable
  public abstract boolean isLoaded();

  public abstract void setLoaded( boolean loaded );
}
