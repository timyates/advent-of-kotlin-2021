package com.bloidonia.advent.day19

import spock.lang.Specification

class Day19Spec extends Specification {

    def "distance works"() {
        when:
        def dist = new Vector(1, 1, 0).distance(new Vector(2, 1, 2))
        def asStr = String.format("%.2f", dist)

        then:
        asStr == "2.24"
    }

    def "fingerprinting works"() {
        when:
        def input = '''--- scanner 0 ---
                             |404,-588,-901
                             |528,-643,409
                             |-838,591,734
                             |390,-675,-793
                             |-537,-823,-458
                             |-485,-357,347
                             |-345,-311,381
                             |-661,-816,-575
                             |-876,649,763
                             |-618,-824,-621
                             |553,345,-567
                             |474,580,667
                             |-447,-329,318
                             |-584,868,-557
                             |544,-627,-890
                             |564,392,-477
                             |455,729,728
                             |-892,524,684
                             |-689,845,-530
                             |423,-701,434
                             |7,-33,-71
                             |630,319,-379
                             |443,580,662
                             |-789,900,-551
                             |459,-707,401
                             |
                             |--- scanner 1 ---
                             |686,422,578
                             |605,423,415
                             |515,917,-361
                             |-336,658,858
                             |95,138,22
                             |-476,619,847
                             |-340,-569,-846
                             |567,-361,727
                             |-460,603,-452
                             |669,-402,600
                             |729,430,532
                             |-500,-761,534
                             |-322,571,750
                             |-466,-666,-811
                             |-429,-592,574
                             |-355,545,-477
                             |703,-491,-529
                             |-328,-685,520
                             |413,935,-424
                             |-391,539,-444
                             |586,-435,557
                             |-364,-763,-893
                             |807,-499,-711
                             |755,-354,-619
                             |553,889,-390'''.stripMargin()
        def scanners = use(Day19Kt) { input.toScanners() }
        def vals = scanners[0].readings.indexed().collect { idx, _ ->
            [_, scanners[1].readings.indexed().find { idx2, _2 ->
                scanners[0].distance(idx).intersect(scanners[1].distance(idx2)).size() >= 12
            }]
        }.findAll { it[1] }
        vals.each {
            println it
        }
        then:
        vals == []
    }
}
