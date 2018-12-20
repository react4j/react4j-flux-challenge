package react4j.sithtracker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.arez.ReactArezComponent;
import react4j.dom.proptypes.html.HtmlProps;
import react4j.sithtracker.model.Sith;
import react4j.sithtracker.model.SithTrackerModel;
import static react4j.dom.DOM.*;

@ReactComponent
public abstract class SithTrackerView
  extends ReactArezComponent
{
  @Inject
  SithTrackerModel _model;

  @Nullable
  @Override
  protected ReactNode render()
  {
    return div( new HtmlProps().className( "app-container" ),
                div( new HtmlProps().className( "css-root" ),
                     h1( new HtmlProps().className( "css-planet-monitor" ),
                         "Obi-Wan currently on " + _model.getCurrentWorld().getName() ),
                     section( new HtmlProps().className( "css-scrollable-list" ),
                              ul( new HtmlProps().className( "css-slots" ),
                                  fragment( _model.getSithWindow().stream().map( this::renderSith ) )
                              ),
                              div( new HtmlProps().className( "css-scroll-buttons" ),
                                   ScrollButtonBuilder
                                     .className( "up" )
                                     .onClick( () -> _model.scrollUp() )
                                     .enabled( _model.canScrollUp() ),
                                   ScrollButtonBuilder
                                     .className( "down" )
                                     .onClick( () -> _model.scrollDown() )
                                     .enabled( _model.canScrollDown() )
                              )
                     )
                )
    );
  }

  @Nonnull
  private ReactNode renderSith( @Nullable final Sith sith )
  {
    return null == sith ? SithViewBuilder.sith( null ) : SithViewBuilder.key( sith.getId() ).sith( sith );
  }
}
