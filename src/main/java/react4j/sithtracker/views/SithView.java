package react4j.sithtracker.views;

import java.util.Objects;
import javax.annotation.Nonnull;
import react4j.ReactNode;
import react4j.annotations.Prop;
import react4j.annotations.Render;
import react4j.annotations.View;
import react4j.dom.proptypes.html.CssProps;
import react4j.dom.proptypes.html.HtmlProps;
import react4j.sithtracker.model.Planet;
import react4j.sithtracker.model.SithModel;
import react4j.sithtracker.model.SithTrackerModel;
import static react4j.dom.DOM.*;

@View( type = View.Type.MAYBE_TRACKING )
abstract class SithView
{
  @Nonnull
  private final SithTrackerModel _model;

  SithView( @Nonnull final SithTrackerModel model )
  {
    _model = Objects.requireNonNull( model );
  }

  @Prop( immutable = true )
  @Nonnull
  abstract SithModel sith();

  @Nonnull
  @Render
  ReactNode render()
  {
    final SithModel sith = sith();
    final Planet currentPlanet = _model.getCurrentPlanet();
    final Planet homeworld = sith.getData().getHomeworld();
    final boolean livesOnCurrentWorld = currentPlanet.getId() == homeworld.getId();
    return li( new HtmlProps()
                 .className( "css-slot" )
                 .style( new CssProps().color( livesOnCurrentWorld ? "red" : null ) ),
               h3( sith.getData().getName() ),
               h6( "Home world: " + homeworld.getName() ) );
  }
}
