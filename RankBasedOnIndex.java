class Person {
	int height;
	int index;
}

class Solution {
	List<Person> rank(List<Person> input) {
		List<Person> result = new ArrayList<Person>();
		
		if (input.size() == 0 || input == null) {
			return result;
		}
		
		Collections.sort(input, new Comparator<Person>(){
			int compare(Person p1, Person p2) {
				if (p1.height > p2.height) {
					return -1;
				} else if (p1.height < p2.height) {
					return 1;
				} else {
					return 0;
				}
			}
		});
		
		//time complexity if O(n^2)
		for (int i = 0; i < input.size(); i++) {
			result.add(input.get(i).index, input.get(i));
		}
		
		return result;
	}
}