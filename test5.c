void main () {
	char c;
	print("input c:");
	scan(c);

	if ((c >= '0') && (c <= '9')) {
		print("����");
	} else {
		if (((c >= 'a') && (c <= 'z')) ||
				((c >= 'A') && (c <= 'Z'))) {
			print("��ĸ");
		} else {
			print("����");
		}
	}
}
