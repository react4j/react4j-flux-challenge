package react4j.sithtracker;

import arez.SafeProcedure;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import react4j.Component;
import react4j.ReactNode;
import react4j.annotations.Prop;
import react4j.annotations.ReactComponent;
import react4j.dom.proptypes.html.BtnProps;
import static react4j.dom.DOM.*;

@ReactComponent
public abstract class ScrollButton
  extends Component
{
  @Prop
  @Nonnull
  abstract String className();

  @Prop
  @Nonnull
  abstract SafeProcedure onClick();

  @Prop
  abstract boolean enabled();

  @Nullable
  @Override
  protected ReactNode render()
  {
    return button( new BtnProps()
                     .className( "css-button-" + className(), enabled() ? null : "css-button-disabled" )
                     .onClick( e -> onClick().call() ) );
  }
}
