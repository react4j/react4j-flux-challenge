package react4j.sithtracker.dagger;

import dagger.Component;
import javax.inject.Singleton;
import react4j.sithtracker.model.SithTrackerModelDaggerModule;
import react4j.sithtracker.views.SithListViewDaggerFactory;
import react4j.sithtracker.views.SithTrackerViewDaggerFactory;
import react4j.sithtracker.views.SithViewDaggerFactory;

@Singleton
@Component( modules = { SithTrackerModelDaggerModule.class } )
public interface SithTrackerComponent
  extends SithTrackerViewDaggerFactory,
          SithListViewDaggerFactory,
          SithViewDaggerFactory
{
  static SithTrackerComponent create()
  {
    final SithTrackerComponent component = DaggerSithTrackerComponent.create();
    component.bindSithTrackerView();
    component.bindSithListView();
    component.bindSithView();
    return component;
  }
}
