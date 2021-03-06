package Editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

import Editor.edit.SceneEditorTransferHandler;
import Editor.selection.SelectionHandler;
import Editor.selection.SelectionListener;
import Editor.tile.TMComponent;
import Editor.tools.Tool;
import EditorGUI.UndoManager;
import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Game.Scene;
import Graphic.Camera;
import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;
import Maths.Vector2D;
import Editor.actions.Actions;

public class SceneEditor extends JPanel
	implements MouseListener, MouseMotionListener, MouseWheelListener,
			   KeyListener, FocusListener, 
			   SceneListener, ResourceListener, SelectionListener,
			   ActionSupportNotifier
{
	private EditorResources resources;
	
	private Scene scene;
	
	private Tool currTool;
	
	private Point2D.Double focus;
	
	private Point startp, currp;

	private List<ActionSupportListener> lists;
	
	private JPopupMenu popup;
	
	public SceneEditor(EditorResources resources)
	{
		this.resources = resources;
		
		scene = resources.scene;
				
		currTool = resources.getTool();
	
		focus = new Point2D.Double();
		
		lists = new ArrayList<ActionSupportListener>();
	
		popup = new JPopupMenu();
		
		addMouseListener(currTool);
		addMouseMotionListener(currTool);
		
		//SceneCompMaintainer maintainer = new SceneCompMaintainer(
			//	resources, this);
		
		TMComponent tmComp = new TMComponent(resources, this);
				
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		addFocusListener(this);
		
		resources.getSelectionHandler()
				 .addSelectionListener(this);
		
		setLayout(null);
		resources.addSceneListener(this);
		resources.addResourceListener(this);
		setPreferredSize(new Dimension(800, 600));
	
		add(tmComp);		
		
		setTransferHandler(new SceneEditorTransferHandler(resources));
		
		ActionMap map = getActionMap();
		Action copy = TransferHandler.getCopyAction();
		
		map.put(copy.getValue(Action.NAME), copy);
		
		popup.add(Actions.COPY);
		popup.add(Actions.CUT);
		popup.add(Actions.PASTE);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Dimension size = getSize();
		
		Camera camera = resources.getCamera();
		
		camera.setScreenDimension(size);
		
		GraphicsContext gc = new GraphicsContext(g, camera);
	
		scene.paint(gc);
		
		paintSelectedEntitiesHighlight(gc);
		
		if(currTool != null)
			currTool.paint(g);
		
		gc.g2d.dispose();
	}
	
	private void paintSelectedEntitiesHighlight(GraphicsContext gc)
	{
		SelectionHandler handler = resources.getSelectionHandler();
		
		if(handler.sceneSelection())
		{
			List<Object> selection = resources.getSelectionHandler()
					  .getSelection();

			for(Object obj : selection)
			{
				ShapeGraphic graph = new ShapeGraphic();

				graph.setPaint(new Color(0, 0, 255, 120));
				
				if(obj instanceof Entity)
				{
					Entity ent = (Entity) obj;
					
					Rectangle2D.Double graphBound = 
							ent.get(GraphicsComponent.class)
							   .getGraphic()
							   .getBound();

					graph.setShape(graphBound);
				
					graph.paint(gc);
				}
			}
		}
	}
	
	@Override
	public void toolChanged(
			Tool prevTool, Tool newTool)
	{
		removeMouseListener(prevTool);
		removeMouseMotionListener(prevTool);
		
		addMouseListener(newTool);
		addMouseMotionListener(newTool);
		
		currTool = newTool;
		
		currTool.prepare();
	}
	
	protected void addImpl(
			Component comp, Object constraints,
			int index)
	{
		super.addImpl(comp, constraints, index);
		comp.addFocusListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
	
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{		
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		startp = e.getPoint();
		
		Point2D.Double focus = resources.getCamera()
									    .getFocus();
		
		this.focus.x = focus.x;
		this.focus.y = focus.y;
		
		requestFocusInWindow();
		
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		repaint();
		
		if(e.isPopupTrigger())
		{
			int x = e.getX(), y = e.getY();
			
			popup.show(this, x, y);
		}
	}

	private void slide()
	{
		Camera camera = resources.getCamera();
		
		Vector2D.Double shift = 
				camera.normalVector(startp.x - currp.x, startp.y - currp.y);
		
		camera.setFocus(focus.x + shift.x, focus.y + shift.y);
	
		resources.notifyOfSceneChange();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		currp = e.getPoint();
		
		if(SwingUtilities.isMiddleMouseButton(e))
			slide();
		
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		//if(currSTH == null)
			//return;
		
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		Camera camera = resources.getCamera();
		
		int kc = e.getKeyCode();
		
		switch(kc)
		{
			case KeyEvent.VK_UP:
				camera.moveFocus(0, -5);
				break;
			case KeyEvent.VK_DOWN:
				camera.moveFocus(0, 5);
				break;
			case KeyEvent.VK_LEFT:
				camera.moveFocus(-5, 0);
				break;
			case KeyEvent.VK_RIGHT:
				camera.moveFocus(5, 0);
				break;
		}
		
		resources.notifyOfSceneChange();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sceneChanged() {
		
		repaint();
	}

	@Override
	public void focusGained(FocusEvent e) 
	{
		UndoManager undoManager = resources.getUndoManager();
		
		resources.getUndoAction()
				 .setUndoManager(undoManager);
		
		resources.getRedoAction()
				 .setUndoManager(undoManager);		
	}

	@Override
	public void focusLost(FocusEvent e) 
	{
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) 
	{
		int rotation = e.getWheelRotation();
		
		Camera camera = resources.getCamera();
		
		camera.moveFocus(0, rotation * 5);

		resources.notifyOfSceneChange();
	}
	
	@Override
	public void selectionChanged()
	{
		for(ActionSupportListener list : lists)
			list.actionSupportChanged(this);
	}

	@Override
	public void selectionModified() {
		repaint();
	}

	@Override
	public void addActionSupportListener(
			ActionSupportListener list) {
		lists.add(list);
	}

	@Override
	public void removeActionSupportListener(ActionSupportListener list) {
		lists.remove(list);
	}

	@Override
	public boolean actionSupported(Action action) 
	{
		if(action.equals(TransferHandler.getCopyAction()) ||
		   action.equals(TransferHandler.getCutAction()))
		{
			SelectionHandler handler = resources.getSelectionHandler();
			
			return handler.selectionCount() > 0 & handler.sceneSelection();
		}
		
		return false;
	}

}