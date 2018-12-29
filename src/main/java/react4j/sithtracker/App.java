package react4j.sithtracker;

import com.google.gwt.core.client.EntryPoint;
import elemental2.dom.DomGlobal;
import react4j.dom.ReactDOM;
import react4j.sithtracker.dagger.SithTrackerComponent;
import react4j.sithtracker.views.SithTrackerViewBuilder;

public class App
  implements EntryPoint
{
  @Override
  public void onModuleLoad()
  {
    // Uncomment this line for more detailed event logging
    //ReactArezSpyUtil.enableSpyEventLogging();

    // TODO: Having to add the next line feels super bad. Other frameworks (VueJS/Angular2+)
    // combine the next two steps with the instance render and injection setup combined
    SithTrackerComponent.create();
    ReactDOM.render( SithTrackerViewBuilder.build(), DomGlobal.document.getElementById( "app" ) );
  }
}
