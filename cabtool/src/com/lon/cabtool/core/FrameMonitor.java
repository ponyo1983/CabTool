package com.lon.cabtool.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FrameMonitor {

	Queue<SerialFrame> listFrames = new LinkedList<SerialFrame>();

	List<int[]> filterList = new ArrayList<int[]>();
	List<Boolean> enableList = new ArrayList<Boolean>();

	public void addFilter(int[] filter) {
		filterList.add(filter);
		enableList.add(true);
	}

	public void addFilter(int[][] filters) {
		for (int[] filter : filters) {
			filterList.add(filter);
			enableList.add(true);
		}
	}

	public void enableFilter(int index) {
		if (index > 0 && index < enableList.size()) {
			enableList.set(index, true);
		}
	}

	public void disableFilter(int index) {
		if (index > 0 && index < enableList.size()) {
			enableList.set(index, false);
		}
	}

	public void addFrame(SerialFrame frame) {
		if (filterList.size() > 0) {
			byte[] data = frame.getData();
			boolean match = false;
			boolean thisMatch;
			for (int j = 0; j < filterList.size(); j++) {
				if (enableList.get(j) == false)
					continue;
				int[] filter = filterList.get(j);
				thisMatch = true;
				for (int i = 0; i < filter.length; i++) {
					if (filter[i] > 0 && filter[i] != (data[i]&0x0ff)) {
						thisMatch = false;
						break;
					}
				}
				if (thisMatch) {
					match = true;
					break;
				}
			}

			if (!match)
				return;
		}
		synchronized (listFrames) {
			if (listFrames.size() > 100) {
				listFrames.remove();
			}
			listFrames.add(frame);
			listFrames.notifyAll();
		}

	}

	public void clearFrame()
	{
		synchronized (listFrames) {
			listFrames.clear();
		}
		
	}
	public SerialFrame getFrame(int timeout) {
		SerialFrame frame = null;
		synchronized (listFrames) {

			int size = listFrames.size();
			if (size > 0) {
				frame = listFrames.remove();
			}

			try {
				if (timeout < 0) {
					listFrames.wait();
				} else if (timeout > 0) {
					listFrames.wait(timeout);
				}

			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}
		if (frame != null || timeout == 0)
			return frame;

		frame = getFrame(0);

		return frame;
	}

}
