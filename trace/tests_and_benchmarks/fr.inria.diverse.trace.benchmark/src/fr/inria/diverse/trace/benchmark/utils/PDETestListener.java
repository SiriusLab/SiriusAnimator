package fr.inria.diverse.trace.benchmark.utils;

import java.io.File;

import org.eclipse.jdt.internal.junit.model.ITestRunListener2;

import junit.framework.TestCase;
import junit.framework.TestResult;

@SuppressWarnings("restriction")
public class PDETestListener implements ITestRunListener2 {
	private Object resultsCollector;
	private int totalNumberOfTests;
	private int testsRunCount;
	private int numberOfTestsPassed;
	private int numberOfTestsFailed;
	private int numberOfTestsWithError;
	private boolean testRunEnded = false;
	private File outputFile;
	private String suiteName;

	public PDETestListener(Object collector, String suite) {
		resultsCollector = collector;
		suiteName = suite;
	}

	public void setOutputFile(String filename) {
		outputFile = new File(filename);
	}

	public File getOutputFile() {
		if (outputFile == null) {
			setOutputFile("TEST-" + suiteName + ".xml");
		}
		return outputFile;
	}

	public boolean failed() {
		return ((numberOfTestsFailed + numberOfTestsWithError) > 0) || (testRunEnded && (testsRunCount == 0));
	}

	public int count() {
		return testsRunCount;
	}

	public synchronized void testRunStarted(int testCount) {
		totalNumberOfTests = testCount;
		testsRunCount = 0;
		numberOfTestsPassed = 0;
		numberOfTestsFailed = 0;
		numberOfTestsWithError = 0;
		testRunEnded = false;
		System.out.println("PDE Test Run Started - running " + totalNumberOfTests + " tests ...");
	}

	public synchronized void testRunEnded(long elapsedTime) {
		testRunEnded = true;
		System.out.println("Test Run Ended   - " + (failed() ? "FAILED" : "PASSED") + " - Total: " + totalNumberOfTests
				+ " (Errors: " + numberOfTestsWithError + ", Failed: " + numberOfTestsFailed + ", Passed: "
				+ numberOfTestsPassed + "), duration " + elapsedTime + "ms.");

		synchronized (resultsCollector) {
			resultsCollector.notifyAll();
		}
	}

	public synchronized void testRunStopped(long elapsedTime) {
		System.out.println("Test Run Stopped");
		testRunEnded(elapsedTime);
	}

	public synchronized void testRunTerminated() {
		System.out.println("Test Run Terminated");
		testRunEnded(0);
	}

	public synchronized void testStarted(String testId, String testName) {
		testsRunCount++;
		new WrapperTestCase(testName);
		System.out.println("  Test Started - " + count() + " - " + testName);
	}

	public synchronized void testEnded(String testId, String testName) {
		numberOfTestsPassed = count() - (numberOfTestsFailed + numberOfTestsWithError);
		System.out.println("  Test Ended   - " + count() + " - " + testName);
	}

	public synchronized void testFailed(int status, String testId, String testName, String trace, String expected,
			String actual) {
		String statusMessage = String.valueOf(status);
		if (status == ITestRunListener2.STATUS_OK) {
			numberOfTestsPassed++;
			statusMessage = "OK";
		} else if (status == ITestRunListener2.STATUS_FAILURE) {
			numberOfTestsFailed++;
			statusMessage = "FAILED";
		} else if (status == ITestRunListener2.STATUS_ERROR) {
			numberOfTestsWithError++;
			statusMessage = "ERROR";
		}
		System.out.println("  Test Failed  - " + count() + " - " + testName + " - status: " + statusMessage
				+ ", trace: " + trace + ", expected: " + expected + ", actual: " + actual);
	}

	public synchronized void testReran(String testId, String testClass, String testName, int status, String trace,
			String expected, String actual) {
		String statusMessage = String.valueOf(status);
		if (status == ITestRunListener2.STATUS_OK) {
			statusMessage = "OK";
		} else if (status == ITestRunListener2.STATUS_FAILURE) {
			statusMessage = "FAILED";
		} else if (status == ITestRunListener2.STATUS_ERROR) {
			statusMessage = "ERROR";
		}

		System.out.println("  Test ReRan   - " + testName + " - test class: " + testClass + ", status: " + statusMessage
				+ ", trace: " + trace + ", expected: " + expected + ", actual: " + actual);
	}

	public synchronized void testTreeEntry(String description) {
		System.out.println("Test Tree Entry - Description: " + description);
	}

	class WrapperTestCase extends TestCase {

		public WrapperTestCase(String name) {
			super(name);
		}

		public int countTestCases() {
			return 1;
		}

		public void run(TestResult result) {
		}
	}
}
