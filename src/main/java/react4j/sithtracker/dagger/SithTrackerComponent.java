package react4j.sithtracker.dagger;

import dagger.Component;
import javax.inject.Singleton;
import react4j.sithtracker.model.SithTrackerModelDaggerModule;
import react4j.sithtracker.views.SithListViewFactory;
import react4j.sithtracker.views.SithTrackerViewFactory;
import react4j.sithtracker.views.SithViewFactory;

@SuppressWarnings( "UnusedReturnValue" )
@Singleton
@Component( modules = SithTrackerModelDaggerModule.class )
public interface SithTrackerComponent
{
  SithTrackerViewFactory sithTrackerViewFactory();

  SithListViewFactory sithListViewFactory();

  SithViewFactory sithViewFactory();

  static SithTrackerComponent create()
  {
    final SithTrackerComponent component = DaggerSithTrackerComponent.create();
    component.sithTrackerViewFactory();
    component.sithListViewFactory();
    component.sithViewFactory();
    return component;
  }
}
