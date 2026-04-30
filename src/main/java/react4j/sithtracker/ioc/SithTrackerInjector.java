package react4j.sithtracker.ioc;

import javax.annotation.Nonnull;
import react4j.sithtracker.model.ModelFragment;
import react4j.sithtracker.views.ViewsFragment;
import sting.Injector;

@Injector( includes = { ModelFragment.class, ViewsFragment.class } )
public interface SithTrackerInjector
{
  @Nonnull
  static SithTrackerInjector create()
  {
    return new Sting_SithTrackerInjector();
  }
}
