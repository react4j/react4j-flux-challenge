package react4j.sithtracker.views;

import javax.annotation.Nonnull;
import react4j.ReactNode;
import react4j.annotations.Render;
import react4j.annotations.View;
import react4j.dom.proptypes.html.HtmlProps;
import static react4j.dom.DOM.*;

@View( type = View.Type.STATEFUL )
abstract class EmptySithView
{
  @Nonnull
  @Render
  ReactNode render()
  {
    return li( new HtmlProps().className( "css-slot" ) );
  }
}
