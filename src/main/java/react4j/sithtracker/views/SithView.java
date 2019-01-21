package react4j.sithtracker.views;

import javax.annotation.Nullable;
import javax.inject.Inject;
import react4j.ReactNode;
import react4j.annotations.Prop;
import react4j.annotations.ReactComponent;
import react4j.arez.ReactArezComponent;
import react4j.dom.proptypes.html.CssProps;
import react4j.dom.proptypes.html.HtmlProps;
import react4j.sithtracker.model.Sith;
import react4j.sithtracker.model.SithTrackerModel;
import static react4j.dom.DOM.*;

@ReactComponent( allowNoArezDeps = true )
public abstract class SithView
  extends ReactArezComponent
{
  @Inject
  SithTrackerModel _model;

  @Prop
  @Nullable
  abstract Sith sith();

  @Nullable
  @Override
  protected ReactNode render()
  {
    final Sith sith = sith();
    if ( null != sith )
    {
      final boolean livesOnCurrentWorld = _model.getCurrentPlanet().getId() == sith.getHomeworld().getId();
      return li( new HtmlProps()
                   .className( "css-slot" )
                   .style( new CssProps().color( livesOnCurrentWorld ? "red" : null ) ),
                 h3( sith.getName() ),
                 h6( "Home world: " + sith.getHomeworld().getName() ) );
    }
    else
    {
      return li( new HtmlProps().className( "css-slot" ) );
    }
  }
}
