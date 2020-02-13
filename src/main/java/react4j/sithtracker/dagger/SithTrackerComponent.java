package react4j.sithtracker.dagger;

import dagger.Component;
import javax.inject.Singleton;
import react4j.sithtracker.model.SithTrackerModelDaggerModule;
import react4j.sithtracker.views.SithListViewDaggerComponentExtension;
import react4j.sithtracker.views.SithTrackerViewDaggerComponentExtension;
import react4j.sithtracker.views.SithViewDaggerComponentExtension;

@Singleton
@Component( modules = SithTrackerModelDaggerModule.class )
public interface SithTrackerComponent
  extends SithTrackerViewDaggerComponentExtension,
          SithListViewDaggerComponentExtension,
          SithViewDaggerComponentExtension
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
