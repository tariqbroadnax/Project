package ToolBar;

public class NewFileToolBarButton extends ToolBarButton
{
	public NewFileToolBarButton()
	{
		super();
		setToolTipText("New Scene");
	}

	@Override
	protected String iconFileName() {
		return "jlfgr-1.0\\toolbarButtonGraphics\\"
				+ "general\\Add16.gif";
	}
}
