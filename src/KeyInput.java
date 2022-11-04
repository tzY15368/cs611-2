interface ToMoveDir {
    MoveDir toMoveDir();
}

public enum KeyInput implements ToMoveDir{
    W {
        public MoveDir toMoveDir(){
            return MoveDir.Up;
        }
    },
    A {
        public MoveDir toMoveDir(){
            return MoveDir.Left;
        }
    },
    S {
        public MoveDir toMoveDir(){
            return MoveDir.Down;
        }
    },
    D {
        public MoveDir toMoveDir(){
            return MoveDir.Right;
        }
    },
    Y {
        public MoveDir toMoveDir(){
            return null;
        }
    },
    N {
        public MoveDir toMoveDir(){
            return null;
        }
    },
    I {
        public MoveDir toMoveDir(){
            return null;
        }
    },
    M {
        public MoveDir toMoveDir(){
            return null;
        }
    }
}
