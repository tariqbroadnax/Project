package ToolBar;

public class SaveToolBarButton extends ToolBarButton
{
	public SaveToolBarButton()
	{
		super();
		setToolTipText("Save");
	}

	@Override
	protected String iconFileName() {
		return "jlfgr-1.0\\toolbarButtonGraphics\\"
		+ "general\\save16.gif";
	}
}