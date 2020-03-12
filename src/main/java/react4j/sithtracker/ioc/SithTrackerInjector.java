package react4j.sithtracker.ioc;

import javax.annotation.Nonnull;
import react4j.sithtracker.model.SithTrackerModel;
import react4j.sithtracker.views.SithListViewFactory;
import react4j.sithtracker.views.SithTrackerViewFactory;
import react4j.sithtracker.views.SithViewFactory;
import sting.Injector;

@Injector( includes = { SithTrackerViewFactory.class,
                        SithListViewFactory.class,
                        SithViewFactory.class,
                        SithTrackerModel.class } )
public interface SithTrackerInjector
{
  @Nonnull
  static SithTrackerInjector create()
  {
    return new Sting_SithTrackerInjector();
  }
}
