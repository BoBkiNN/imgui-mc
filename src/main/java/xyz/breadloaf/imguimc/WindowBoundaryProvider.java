package xyz.breadloaf.imguimc;

import com.mojang.blaze3d.platform.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

public interface WindowBoundaryProvider {
    int getHeight();
    int getWidth();
    int getX();
    int getY();

    default String asString() {
        return "WindowBoundary("+getX()+","+getY()+";"+getWidth()+","+getHeight()+")";
    }

    WindowBoundaryProvider SCREEN_WORK_AREA = new WindowBoundaryProvider() {

        public static int[] getMonitorWorkArea(long monitor) {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer x = stack.mallocInt(1);
                IntBuffer y = stack.mallocInt(1);
                IntBuffer width = stack.mallocInt(1);
                IntBuffer height = stack.mallocInt(1);

                // Get the usable work area (excludes taskbar and docks)
                GLFW.glfwGetMonitorWorkarea(monitor, x, y, width, height);
                return new int[]{x.get(0), y.get(0), width.get(0), height.get(0)};
            }
        }


        @Override
        public int getHeight() {
            long primaryMonitor = GLFW.glfwGetPrimaryMonitor();
            return getMonitorWorkArea(primaryMonitor)[3];
        }

        @Override
        public int getWidth() {
            long primaryMonitor = GLFW.glfwGetPrimaryMonitor();
            return getMonitorWorkArea(primaryMonitor)[2];
        }

        @Override
        public int getX() {
            long primaryMonitor = GLFW.glfwGetPrimaryMonitor();
            return getMonitorWorkArea(primaryMonitor)[0];
        }

        @Override
        public int getY() {
            long primaryMonitor = GLFW.glfwGetPrimaryMonitor();
            return getMonitorWorkArea(primaryMonitor)[1];
        }
    };

    WindowBoundaryProvider MINECRAFT = new WindowBoundaryProvider() {
        private final Window window = ImGuiMc.MINECRAFT.getWindow();

        @Override
        public int getHeight() {
            return window.getHeight();
        }

        @Override
        public int getWidth() {
            return window.getWidth();
        }

        @Override
        public int getX() {
            return window.getX();
        }

        @Override
        public int getY() {
            return window.getY();
        }
    };
}
