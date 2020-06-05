package react4j.sithtracker.views;

import arez.SafeProcedure;
import javax.annotation.Nonnull;
import react4j.ReactNode;
import react4j.annotations.Input;
import react4j.annotations.Render;
import react4j.annotations.View;
import react4j.dom.proptypes.html.BtnProps;
import static react4j.dom.DOM.*;

@View
abstract class ScrollButton
{
  @Input
  @Nonnull
  abstract String className();

  @Input
  @Nonnull
  abstract SafeProcedure onClick();

  @Input
  abstract boolean enabled();

  @Nonnull
  @Render
  ReactNode render()
  {
    return button( new BtnProps()
                     .className( "css-button-" + className(), enabled() ? null : "css-button-disabled" )
                     .onClick( e -> onClick().call() ) );
  }
}
