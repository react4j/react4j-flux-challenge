package react4j.sithtracker.views;

import javax.annotation.Nullable;
import react4j.Component;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.dom.proptypes.html.HtmlProps;
import static react4j.dom.DOM.*;

@ReactComponent( type = ReactComponent.Type.STATEFUL )
public abstract class EmptySithView
  extends Component
{
  @Nullable
  @Override
  protected ReactNode render()
  {
    return li( new HtmlProps().className( "css-slot" ) );
  }
}
