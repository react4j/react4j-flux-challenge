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
    int[] sithIndex = new int[ 0 ];
    return div( new HtmlProps().className( "app-container" ),
                div( new HtmlProps().className( "css-root" ),
                     h1( new HtmlProps().className( "css-planet-monitor" ),
                         "Obi-Wan currently on " + _model.getCurrentWorld().getName() ),
                     section( new HtmlProps().className( "css-scrollable-list" ),
                              ul( new HtmlProps().className( "css-slots" ),
                                  fragment( _model.getSithWindow()
                                              .stream()
                                              .map( sith -> renderSith( sith, sithIndex ) ) )
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
  private ReactNode renderSith( @Nonnull final Sith sith, final int[] sithIndex )
  {
    // TODO: Ugly hack required here as key can not be null. Should instead use null when sith not present
    final String key = String.valueOf( null != sith ? sith.getId() : -( sithIndex[ 0 ]++ ) );
    return SithViewBuilder.key( key ).sith( sith );
  }
}
