package react4j.sithtracker.model;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Sith
{
  private final int _id;
  @Nonnull
  private final String _name;
  @Nonnull
  private final World _homeworld;
  @Nullable
  private final Integer _masterId;
  @Nullable
  private final Integer _apprenticeId;

  public Sith( final int id,
               @Nonnull final String name,
               @Nonnull final World homeworld,
               @Nullable final Integer masterId,
               @Nullable final Integer apprenticeId )
  {
    _id = id;
    _name = Objects.requireNonNull( name );
    _homeworld = Objects.requireNonNull( homeworld );
    _masterId = masterId;
    _apprenticeId = apprenticeId;
  }

  public int getId()
  {
    return _id;
  }

  @Nonnull
  public String getName()
  {
    return _name;
  }

  @Nonnull
  public World getHomeworld()
  {
    return _homeworld;
  }

  @Nullable
  public Integer getMasterId()
  {
    return _masterId;
  }

  @Nullable
  public Integer getApprenticeId()
  {
    return _apprenticeId;
  }
}
