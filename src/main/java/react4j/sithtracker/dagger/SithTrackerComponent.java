package react4j.sithtracker.dagger;

import dagger.Component;
import javax.inject.Singleton;
import react4j.sithtracker.SithTrackerViewDaggerFactory;
import react4j.sithtracker.SithViewDaggerFactory;
import react4j.sithtracker.model.SithTrackerModelDaggerModule;

@Singleton
@Component( modules = { SithTrackerModelDaggerModule.class } )
public interface SithTrackerComponent
  extends SithTrackerViewDaggerFactory,
          SithViewDaggerFactory
{
  static SithTrackerComponent create()
  {
    final SithTrackerComponent component = DaggerSithTrackerComponent.create();
    component.bindSithTrackerView();
    component.bindSithView();
    return component;
  }
}
