val puzzleAlgorithm = "#.#.#.#.#......#.#.#.#.##..#.##.##..#..##...#.#.#.#...##.##.##.###....#..#...#.#..###.#...#..##.#.###..#..####.###...#.#.#..##..##.##..##..###..#....#.#....#####.#...###...#.#....###...#..##.##..#..#.##..###..#.##.###..#.####...#.##.....#.###...#.##.##.#.#######...#.###..##..##..#.#.#.#####...#....#.....##.#.#...##.######....#..#......#.#.#.#.##...######.#.#####..#####..#.#.#.#.###.#.#....#..##..#..#.#.#..##....##..#.#.......##...#..####.####.#.#..#.###..#...#......###...#...#.##.#.####..#.#....###.####..#."
val puzzleImage = """
    .#.#.########.##.#...##.####...##..#####...##..#.#.###..#...#.#.##.#.....#..#.###...##..#.###.###.##
    .######.#..##.##...#.#.####.#.###..#..#.##.##...##.#...##..#.######...##....###...###..#.#.##.##....
    ..######.#.#.##.#.##.###.##..#####.####..#......#.##.###.#.#.##...#####.###..###...#..#..#..##.#....
    ###..##.#...##.#.#...######..#.#..##..##.#.##.#.#.#.##.#..####.#.#.##......#.#.#...#.##..#.###.###.#
    #.#.#...#..####...#.#..#.##.####.#..#..###########...#....#.#.##.###..#####.#.#.#.###...##..####.#..
    #.#..#.##.#..#..#..#....##.#.#.#...#..###.#.##.##.#.#.##.##..#.#.####.#######....###..#.######.#.#..
    ......###..#..##.####.##.##..#..#.##.##.#..#...###...####.#..#...###.##.#.#####.....#...#..#...####.
    ####..##.###.#...##..#.##....#..##.##..####.#.#.####.##..#..#.....#.###......#.#....#.#.....#.#..#..
    .###.####.#..###..#.#.#.#...##.####.#..#..##....#...###.#.#....#..#######.###.....#.#.#.##...##.#..#
    ##.#......#.##.###.#.##..##.##..#######.###..##..#.#####..#.#..#.#.#..#..##..##.##...#####.#.##.####
    .#.###....##.......#######..#.########..#..##..#.####.###..#..###.##..#...####.#.#..#.##.######.#..#
    ..###.#.##..##.#....#..#.####.....#.#..#..##.##.###..#.###.....##..#.##..#..#.#...#...#.#########.##
    ..#.#.#.#..#...#.##.#.##.#..#...##.#..........#.###.##.##....#####...#.#####.###.#......#..#.#.#..##
    #....#..#.#.#.####.#####.#.#.####.###......#.....#.#..######....##....#.##..##.##...#.#####.##..##.#
    #.#....#####....###.###.#.#...#.##.#..........#....##.#..##..##.####.##.#.##....##..#.#.##..##.#.###
    ..####...####..##.#.....#........#.#..##..#.#..###.....####...#...#.###....#....##.#..##.#.##....##.
    ...#.###........####...#...##..#..##.#.######...#...#.#.#...###....##..##.#..##.......###.##.###...#
    ##.#####...#.#.##.##.#...#.....#.##.########.....#.##..#.####.##...#......####..#.#..#..#...#.......
    .....###..##.###.#.#.#.....##.#.####...#..######.##....#.##.#.#.#...##.####.####.##....##.#.#.###..#
    .########..#.#.#.##..#..#..#.#..#..#.#.##.###.###.#...#.#..#####..##.###.#.##..###.###.#.#.####...##
    ###..##.##...#...#.....####.#.#......##.####..#......##..#####.#.....#.###.##.....#.##...#...#...###
    #.#.#.....##.##..#..#.###.#..##..##..#....#...##.....##..##.####.#######.....#........###..#.##.#.##
    .#....##..###.#..###.#...###.#...###...#.#..####..######......##.##.##..#...#..#####.####...##..#...
    ####.#..#.##....#.#.#...#.#.#####.###.#..##.#.###.....#..#.##......#.##...##..##...##..####.#...##..
    ....#.####..#..##.####.#######...#..#########.##.##..#..#.##.##.#.###.##.#.#....#####.###...#.####.#
    ..#.....##...###..#.##...#.#...###...#######.##..####.##.##..##.#########..###........##....#..#..#.
    .#.##.#.#.##.....##.#.##..###.###.#.....###...###..#.#.#####.##.#....##.#.##.##.##.#.#....#........#
    .#.#..#.#............#....###.#...####..####....#..#.##..#.##..#####...###.######...##..#####..###..
    ###.#..##.#...##..#.#..#..##.#..#..#..#..#####......##.##..#####..#......#..#####.##.####......##.##
    ##...#...##.#####..#...#.#.....#.#..###.#..#.####.....#.....#.#.#.#.###.####..#.......##.#....#..##.
    #...#.#..#####..###.........#.####.###..#....###...#......#...#.#..#.....#####..#.###...#.#...#.....
    ###....#..##.##.####.###.#.#.##.#.#....##..#....##..#.#.###..######.#.#.#..####.##...####..##..##.##
    .###...##.#.#####.#...#.###..##..###....#.##....#...#.#..#...##.###...#.#.#.#######......###.###.#.#
    .#.#.#.....#..##..#.##.#..#.....#.#.######..#.##.#....###.#....#...#####..#.######.###....###...####
    ####..#....########...#..##..#####.#...#.##..##..###...###.#####....##.#.#..#.###.#.#..#.#.#..#.###.
    .##.##.##.#####...#..#..#.###.#.#..###.#......###.##.#.###..#####.#.#...#.###.#.#.###.##.#...#.##.#.
    #.#.##....#####...##.#.##.######.#.#.##.###.#.......###......##..###.#.###...##..##.#.#...#..##.####
    ##.#....#.##.#..#...#.#...#.##......####.#.#..#..###.#####.#...##.####...#..###.##.#.##..###.####.#.
    #.#..#.#.##...##.....###.#.#.#..##.###.#######........###..#.....####.#.##.##...####.##.#.##.###.###
    ##...#.####..#...#...##.#.##..#.##.###..#.##..##......#..#.#...######..#....#.######..######..####..
    .....#.#.......####.....#..#.####.#...##..##...#....#.########.#.#.##..##.##..####..##.####..#.#.###
    .#..#.#....#..##..#....##...###........#.#..#.##.####....###..#..##...#..##.#.#.###.#.#.#..#.####...
    #.#...###.#....#...##############...###..##...#.#.##..#..#....#...####....##..###.##..##..##.#..##.#
    ##..#....#...#..#..##.#..###....#.#.#.###.#.#.###..###..####.###.#.#.###.#..###.#.###.#.##...#.#####
    #..#..####.....###..#.#...##.######..####..#..#....#####...#...#..#..#.....#.##.####......#..##.###.
    ##.##..###.###.#..###...#.####...#....#........#..#..##..##..##.#.##....#.......#.###...#..###.#....
    #.##.#.#..#..###..###...#...#.###..####.#.#....#.#.......##...#..#......##....##.#####.......#...#..
    ....#..#..##.##.#.##.#.##..#.##..##.##.##.##..##.######.##..##.....#.###..#...#.##.#.#.####.###.###.
    .###....##.##..##..#.###..##.#..#.#.##..##.###..##.###..#......########.#####....##...#...##...##.##
    .###.###.#.#######.......#.#.#.##.##..#.#.#.##..#.##...##.##.#...##.#.#######.#..#..##..#.#..##.##.#
    ....#.###.#.##...##..#...#...#..##...#.##...##.##.##.#.#####.....#.#.#.##..####.####.#..####..#..##.
    ##..#.##.#######.#.#...###.#....###.###..##.###.##.##.#.#..##.....#.##......##.###..##.#.#.##..##...
    ...#####.#.##.#..##....##.###.##.#..##..###......#.#..####.##...##.#.###...##.....#..#..###..#####..
    .#####.#.#..#....####.#####.#..##..#.#####.##..#.#.#..##...#.#..######.###..##....###.#....###...#.#
    ..###......##.##.#.##..#..##.....##....##.##..##.....###.#..##.#.#######..#...####..#.###.#####.###.
    ##..#.######.####..#.#..###.#..#.#..#######.#...#...##.#.###.#.##..##.#......##.#.#..##.#.#.#.####..
    ..#.#.####.##..##.#.#.#..####.#.##...###..#.##..#.##.###..####......#..#..#####.#..#.#####.#.###.###
    #..#.#.....#.#...#..###.####..##..#....##.#.###..##..#.#..#.####...##..##..#..####...#########....##
    .#.....#......##.#..#.####.######..#.#.#.#.##...#..#..#...#.###..#....#..#.#...#####..###.##...#.##.
    .####....#.###..#..###.#...#..###.#.#..#......###.##.#.#.#.#####..#######..####.##.....#....#..##.#.
    ...#.##.#.#..####.#####..##..#.##.###.#####..###...##...##.##..#.####..##.#...####...#..##......###.
    ###...##.....#.###...##.#..#.#......##..#...##.#..##.#.#..#.###..#..#####..#.###..#.#...#..##.#.#.#.
    ##.##.##.#.#.#.####.##..##....#.####.####.####...##.####.#....###....###.###.#..#.####.#...####.#.#.
    ####.###...####.#.##...#...##..##...##....#.#.####..#..#.....#.....##....###.....###...#...##.#.###.
    ######..#..#####...#..#..######.##...#.###.#....####..####.##.#.#..#.#.####.##.####..##...####.##.#.
    ##....#.#...#..###..#.##.#.#...###..####.....#.##.#.####..#.##....##...##########..##...###.#.###.##
    ..#.#######....#####..##.####.##.#####..###.#.#..####.....###########...##.#....#.#..##.##.###..###.
    ##.....##...#..#....#####...#...#..#.#.#.#.####....##...####.#.#.#.....#.#..........#.###..##.#.#.#.
    .#####...#..#.##..##.#...#.#.##..###..#.#....#...#.#.#..#.#..#..#.###.#...######.#.####..##..##.#...
    ..#.#.#.##..#.##..#.###......###..#....##.###.#..###.#...##.#.#.....#.##....##.##.##.#...####.####.#
    ##..#.##.##.##.#....##.#..#..#.##.###..##.##.#.#.#......####..##.#.#.###.....##.....#.##..####..##.#
    .###.#.##.#..##.##.#.###.#.##.##.#####...#.#..#..#..#..#.####..#.######..#.#.#...#..#####...#.#..##.
    ..#...#.####.####.####...#..#..##..#.##.#.#..#..####..#...#.####.#.###.##..#......#..#...#..#..###..
    .#..#.###.......#..##.#.#..##..#.#..#..##..#.....####.###...#..#.###.##.#..#.#..##..#...##.##.##....
    #....##..#...##.###.#......##..###...##.###..##..###.####.....#...###..#.#...#####.#.#.######..#..#.
    ##.###.....#...#.#..##.#.#..#.#....#...#..##.######.###.#.####.#.######...#.#.#....##.##..#...#...#.
    .......#..#.##..#..##...#.##.#####..###.####.###.#.#..###....#.#........#..#.#.#.......####..#......
    #####...##.######....#.#.##.#..##...#..#..#..#...#..##.###....#.#.......##...#.###.###..####.##.###.
    .###.##.#....#.#..#..#..##....##...##.#...##....#......#####...####.######.#.#.##.##..##.#.#.....#..
    ..#.##..#.####..####.##.#...#..####..##.....###.#...###..#.###.######.#.#.##..#.#.#####.#.##.#.##...
    .##.###########.#..####.#.####..#.####.##########..#.##.#.#.#..#.#..#.#####.....##.#...#.###..#.##..
    ...###.#.#..#.#.#.#....#..##..####.##...###.#....#.#.####.#.#..#..####...####..###.##..######.##....
    #..#####.#######.#.###......#..#.#.##.##.###.#.##.#...#....#.#####..###..##.#.#####.#..#..#.###.#..#
    #.####..#..#.......###..#..####.#.#.#..#.#..#..#.#...#####.#.......####.#.#.##.##.#..#..##.#....##.#
    ###.###...#..#..##.#..#.#..#..#####.#.#####.....#..#..####....##.##.....#.....#.###.#.#.#.##.#.####.
    .#.##.#.#..#.#####.#.##.#.###.#...#..#..#.#...##.#.##......#.#.#####..#......###.########.#..#.###.#
    #.##.....#.####..######..##..#...#.....##..##..#.#.##.###.#.#.#.#.....#...#.#..###..##..##.######.##
    ####......#.##..#.##..##.##..#.##.....#####...#.#...###.#..##..###.##.###..............##.#.#....#.#
    ###...##.##.##.####....####..#...##...#.####.#....#.....#####.....##...##..###.#...##.#...##.##.##..
    #.#.####...###.....###.###.##.###.....##.##.....###..###..##......##..####.#..##.#.##....##.#.#..#.#
    ####...###.#####.#####.......##..##.###..#..##.##..#..###.#..#..###.....##..##..##.##.#..####.#.#.#.
    ######....#.##.##.#.###...##...###.###.#.##.###....##.#......#.##.##.#####...#..##....###...#..##...
    ..#...........##..........#.##.#.##.##.###...##..##.#####.#####.#...##..####.....#.##....#..#.###.##
    .#..##.#...#.#####...##.#.#.###.#.#..##..#..##########..#.#...#...#...##.##.#...##..#.#.#.##.#..#..#
    ..##....##.#..#...#.#...#.#....#..#.#.#####.##.#......#...##..######..##....####..##.##.##.###.###..
    #.......#..############...##....##...#.#....#...##..###..##....####..##....#...##.###...#..#...#####
    ..##..##..#.###.#.##.#.####.#.##...#.#..#.#####...######...######.####...###.#.#.##..#.######..#.#..
    ###.#....#####..#.##.##..###..##.#..#.#.#.##.#......#..#....##.#.#.###..#.#######.###..#..#..#.###..
    #...#.#..#.####.###..##.#.#####...#.#.##..#..#.#####.#.###.##.####..#..##.#.##...##...####.#..#.###.
    ...#.##.##...#.#...####....#..#####...#.#.....####.##.######.#.#...#...###.##..##.#.#.#..#...###.#..
""".trimIndent().split("\n")