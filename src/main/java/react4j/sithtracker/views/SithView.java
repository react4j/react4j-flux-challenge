package react4j.sithtracker.views;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import react4j.Component;
import react4j.ReactNode;
import react4j.annotations.Prop;
import react4j.annotations.ReactComponent;
import react4j.dom.proptypes.html.CssProps;
import react4j.dom.proptypes.html.HtmlProps;
import react4j.sithtracker.model.Planet;
import react4j.sithtracker.model.SithModel;
import react4j.sithtracker.model.SithTrackerModel;
import static react4j.dom.DOM.*;

@ReactComponent( type = ReactComponent.Type.MAYBE_TRACKING )
public abstract class SithView
  extends Component
{
  @Nonnull
  private final SithTrackerModel _model;

  SithView( @Nonnull final SithTrackerModel model )
  {
    _model = Objects.requireNonNull( model );
  }

  @Prop( immutable = true )
  @Nullable
  abstract SithModel sith();

  @Nullable
  @Override
  protected ReactNode render()
  {
    final SithModel sith = sith();
    if ( null != sith )
    {
      final Planet currentPlanet = _model.getCurrentPlanet();
      final Planet homeworld = sith.getData().getHomeworld();
      final boolean livesOnCurrentWorld = currentPlanet.getId() == homeworld.getId();
      return li( new HtmlProps()
                   .className( "css-slot" )
                   .style( new CssProps().color( livesOnCurrentWorld ? "red" : null ) ),
                 h3( sith.getData().getName() ),
                 h6( "Home world: " + homeworld.getName() ) );
    }
    else
    {
      return li( new HtmlProps().className( "css-slot" ) );
    }
  }
}
