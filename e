function BezierCurve(l: location, l2: location, points: location, n: num, p: particle) :: entity:
	
	set {_x1} to x-loc of {_l}
	set {_x2} to x-loc of {_l2}
	set {_y1} to y-loc of {_l}
	set {_y2} to y-loc of {_l2}
	set {_z1} to z-loc of {_l}
	set {_z2} to z-loc of {_l2}
	
	set {_x3} to x-loc of {_points}
	set {_y3} to y-loc of {_points}
	set {_z3} to z-loc of {_points}
	
	loop {_n} times:
		set {_t} to loop-value / {_n}
	
		set {_x} to (1 - {_t})^2 * {_x1} + 2 * (1 - {_t}) * {_t} * {_x3} + {_t}^2 * {_x2}
		set {_y} to (1 - {_t})^2 * {_y1} + 2 * (1 - {_t}) * {_t} * {_y3} + {_t}^2 * {_y2}
		set {_z} to (1 - {_t})^2 * {_z1} + 2 * (1 - {_t}) * {_t} * {_z3} + {_t}^2 * {_z2}
		set {_newLoc} to location({_x}, {_y}, {_z}, world of {_l})
		draw 1 of {_p} at {_newLoc} with extra 0
		
every 1 tick:
	set {_l} to location of spawn of world "World"
	if {PO} >= 360:
		delete {PO}
	add 1.5 to {RX}
	add 1 to {RZ}
	if {RX} >= 360:
		set {RX} to 0
	if {RZ} >= 360:
		set {RZ} to 0
	loop 8 times:
		add 1 to {PO}
		loop 45 times:
			set {_v} to spherical vector radius 9, yaw (loop-value-1 * 8), pitch ((loop-value-2 * 8))
			rotate {_v} around y-axis by {PO}
			rotate {_v} around x-axis by {RX}
			rotate {_v} around z-axis by {RZ}
			if y-loc of ({_l} ~ {_v}) < 63:
				continue
			draw 1 of end rod at ({_l} ~ {_v}) with extra 0
		add 1 to {_f}
		if {_f} >= 8:
			wait a tick
			delete {_f}
