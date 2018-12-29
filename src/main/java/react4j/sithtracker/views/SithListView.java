package react4j.sithtracker.views;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.arez.ReactArezComponent;
import react4j.sithtracker.model.Sith;
import react4j.sithtracker.model.SithTrackerModel;
import static react4j.dom.DOM.*;

@ReactComponent
public abstract class SithListView
  extends ReactArezComponent
{
  @Inject
  SithTrackerModel _model;

  @Nullable
  @Override
  protected ReactNode render()
  {
    return fragment( _model.getSiths().stream().map( this::renderSith ) );
  }

  @Nonnull
  private ReactNode renderSith( @Nullable final Sith sith )
  {
    return null == sith ? SithViewBuilder.sith( null ) : SithViewBuilder.key( sith.getId() ).sith( sith );
  }
}
