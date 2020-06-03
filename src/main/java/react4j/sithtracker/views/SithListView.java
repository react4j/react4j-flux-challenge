package react4j.sithtracker.views;

import java.util.Objects;
import javax.annotation.Nonnull;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.annotations.Render;
import react4j.sithtracker.model.SithTrackerModel;
import static react4j.dom.DOM.*;

@ReactComponent( type = ReactComponent.Type.TRACKING )
abstract class SithListView
{
  @Nonnull
  private final SithTrackerModel _model;

  SithListView( @Nonnull final SithTrackerModel model )
  {
    _model = Objects.requireNonNull( model );
  }

  @Nonnull
  @Render
  ReactNode render()
  {
    return fragment( _model.getSiths().stream().map( sith -> {
      if ( null == sith )
      {
        return EmptySithViewBuilder.build();
      }
      else
      {
        return SithViewBuilder.sith( sith );
      }
    } ) );
  }
}
