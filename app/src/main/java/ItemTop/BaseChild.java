package ItemTop;

/**
 * Created by ChenLei on 2017/6/21.
 */

public abstract class BaseChild {

    private Object childTop;

    public abstract boolean isHasChildTop();

    public Object getChildTop() {
        return childTop;
    }

    public void setChildTop(Object childTop){
        this.childTop = childTop;
    }

}
