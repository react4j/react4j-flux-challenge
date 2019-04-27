package react4j.sithtracker.model;

import java.util.Objects;
import javax.annotation.Nonnull;
import react4j.Keyed;

public final class SithModel
  implements Keyed
{
  @Nonnull
  private final Sith _data;

  SithModel( @Nonnull final Sith data )
  {
    _data = Objects.requireNonNull( data );
  }

  @Nonnull
  public Sith getData()
  {
    return _data;
  }

  @Nonnull
  @Override
  public final String getKey()
  {
    return String.valueOf( _data.getId() );
  }
}
