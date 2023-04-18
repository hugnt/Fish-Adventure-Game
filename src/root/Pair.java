package root;

import java.util.Objects;

public class Pair<T, U> {
    public T first;
    public U second;
    
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		return Objects.equals(first, other.first) && Objects.equals(second, other.second);
	}

       
  
}
