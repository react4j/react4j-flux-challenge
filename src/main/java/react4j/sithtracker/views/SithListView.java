package react4j.sithtracker.views;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import react4j.Component;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.sithtracker.model.SithTrackerModel;
import static react4j.dom.DOM.*;

@ReactComponent( type = ReactComponent.Type.TRACKING )
abstract class SithListView
  extends Component
{
  @Nonnull
  private final SithTrackerModel _model;

  SithListView( @Nonnull final SithTrackerModel model )
  {
    _model = Objects.requireNonNull( model );
  }

  @Nullable
  @Override
  protected ReactNode render()
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
