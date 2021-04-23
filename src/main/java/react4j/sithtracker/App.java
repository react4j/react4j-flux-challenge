package react4j.sithtracker;

import akasha.Element;
import akasha.WindowGlobal;
import arez.Arez;
import arez.spytools.browser.react4j.ReactArezSpyUtil;
import com.google.gwt.core.client.EntryPoint;
import react4j.dom.ReactDOM;
import react4j.sithtracker.ioc.SithTrackerInjector;
import react4j.sithtracker.views.SithTrackerViewBuilder;

public class App
  implements EntryPoint
{
  @Override
  public void onModuleLoad()
  {
    if ( Arez.areSpiesEnabled() )
    {
      ReactArezSpyUtil.enableSpyEventLogging();
    }

    // TODO: Having to add the next line feels super bad. Other frameworks (VueJS/Angular2+)
    // combine initial render and injection in one step.
    SithTrackerInjector.create();
    final Element element = WindowGlobal.document().getElementById( "app" );
    assert null != element;
    ReactDOM.render( SithTrackerViewBuilder.build(), element );
  }
}
