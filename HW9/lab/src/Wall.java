

class Wall{
	protected int hv;
	protected int x;
	protected int y;


	public Wall(int hv, int x, int y){
		this.setWall(hv, x, y);
	}

	public void setWall(int hv, int x, int y){
		this.hv = hv;
		this.x = x;
		this.y = y;
	}
}