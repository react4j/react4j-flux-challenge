package react4j.sithtracker;

import javax.annotation.Nullable;
import javax.inject.Inject;
import react4j.ReactNode;
import react4j.annotations.ReactComponent;
import react4j.arez.ReactArezComponent;
import react4j.dom.proptypes.html.HtmlProps;
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
                         "Obi-Wan currently on " + _model.getCurrentPlanet().getName() ),
                     section( new HtmlProps().className( "css-scrollable-list" ),
                              ul( new HtmlProps().className( "css-slots" ), SithListViewBuilder.build() ),
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
}
